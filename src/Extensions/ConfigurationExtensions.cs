namespace Bazario.ApiGateway.Extensions
{
    public static class ConfigurationExtensions
    {
        public static IConfigurationBuilder AddRoutesConfigurationFiles(
            this IConfigurationBuilder builder)
        {
            builder.AddJsonFile("Configurations/clusters.json", optional: true, reloadOnChange: true);
            builder.AddJsonFile("Configurations/identity.routes.json", optional: true, reloadOnChange: true);
            builder.AddJsonFile("Configurations/users.routes.json", optional: true, reloadOnChange: true);
            builder.AddJsonFile("Configurations/listings.routes.json", optional: true, reloadOnChange: true);
            builder.AddJsonFile("Configurations/search.routes.json", optional: true, reloadOnChange: true);
            builder.AddJsonFile("Configurations/complaints.routes.json", optional: true, reloadOnChange: true);

            return builder;
        }
    }
}
