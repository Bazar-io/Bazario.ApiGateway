using Bazario.AspNetCore.Shared.Domain.Common.Users.Roles;
using Microsoft.AspNetCore.RateLimiting;
using System.Security.Claims;
using System.Threading.RateLimiting;

namespace Bazario.ApiGateway.Extensions
{
    public static class RateLimitingExtensions
    {
        public const string DefaultPolicyName = "default-fixed";

        public static IServiceCollection AddRateLimiting(this IServiceCollection services)
        {
            services.AddRateLimiter(rateLimiterOptions =>
            {
                rateLimiterOptions.RejectionStatusCode = StatusCodes.Status429TooManyRequests;

                rateLimiterOptions.AddDefaultFixedWindowLimiter();
            });

            return services;
        }

        private static void AddDefaultFixedWindowLimiter(
            this RateLimiterOptions options)
        {
            options.AddPolicy(DefaultPolicyName, context =>
            {
                var user = context.User;

                if (IsAdminOrOwner(user))
                {
                    return RateLimitPartition.GetNoLimiter(
                        partitionKey: "no-limit");
                }

                return RateLimitPartition.GetFixedWindowLimiter(
                    partitionKey: context.Connection.RemoteIpAddress?.ToString() ?? "unknown",
                    factory: _ => new FixedWindowRateLimiterOptions
                    {
                        PermitLimit = 100,
                        Window = TimeSpan.FromSeconds(30)
                    });
            });
        }

        private static bool IsAdminOrOwner(ClaimsPrincipal user)
        {
            return user.Identity is not null && user.Identity.IsAuthenticated &&
                (user.IsInRole(Role.Owner.ToString()) || user.IsInRole(Role.Admin.ToString()));
        }
    }
}
