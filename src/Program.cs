using Bazario.ApiGateway.Extensions;
using Bazario.AspNetCore.Shared.Api.Middleware.DependencyInjection;
using Bazario.AspNetCore.Shared.Authentication.DependencyInjection;
using Bazario.AspNetCore.Shared.Authorization.DependencyInjection;

var builder = WebApplication.CreateBuilder(args);

builder.Configuration.AddRoutesConfigurationFiles();

builder.Services.AddOpenApi();

builder.Services.AddAppOptions();

builder.Services.ConfigureAuthentication();
builder.Services.ConfigureAuthorization();

builder.Services.AddReverseProxy()
    .LoadFromConfig(builder.Configuration.GetSection("ReverseProxy"));

var app = builder.Build();

app.Services.ValidateAppOptions();

if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

app.UseHttpsRedirection();

app.UseExceptionHandlingMiddleware();

app.UseAuthentication();
app.UseAuthorization();

app.MapReverseProxy();

app.Run();
