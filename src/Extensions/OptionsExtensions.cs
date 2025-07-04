using Bazario.AspNetCore.Shared.Authentication.Options;
using Bazario.AspNetCore.Shared.Options.DependencyInjection;

namespace Bazario.ApiGateway.Extensions
{
    public static class OptionsExtensions
    {
        public static IServiceCollection AddAppOptions(this IServiceCollection services)
        {
            services.ConfigureValidatableOptions<JwtSettings, JwtSettingsValidator>(
                JwtSettings.SectionName);

            return services;
        }

        public static IServiceProvider ValidateAppOptions(this IServiceProvider serviceProvider)
        {
            serviceProvider.ValidateOptionsOnStart<JwtSettings>();

            return serviceProvider;
        }
    }
}
