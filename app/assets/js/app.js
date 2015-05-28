/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular',
    'home',
    'dashboard',
    'common',
    'profile',
    'services',
    'settings',
    'flipCards'
    ], function(angular) {
    'use strict';

    // We must already declare most dependencies here (except for common), or the submodules' routes
    // will not be resolved
    var app = angular.module('app', [
        'rema7.home'
        ,'rema7.profile'
        ,'rema7.dashboard'
        ,'rema7.common'
        ,'rema7.flipCards'
        ,'rema7.services'
        ,'rema7.settings.products'
        ]);

    app.config(function ($stateProvider, $urlRouterProvider, $httpProvider, $authProvider) {
        $httpProvider.interceptors.push(function($q, $window) {
            return {
                request: function(request) {
                    //request.headers.authorization =
                    //    userService.getAuthorization();
                    return request;
                },
                // This is the responseError interceptor
                responseError: function(rejection) {
                    if (rejection.status === 401) {
                        // Return a new promise

                        $window.location = '/';
                        //return userService.authenticate().then(function() {
                        //    return $injector.get('$http')(rejection.config);
                        //});
                    }

                    /* If not a 401, do nothing with this error.
                     * This is necessary to make a `responseError`
                     * interceptor a no-op. */
                    return $q.reject(rejection);
                }
            };


        });

        $urlRouterProvider.otherwise("/dashboard");
        //$httpProvider.defaults.useXDomain = true;
        //delete $httpProvider.defaults.headers.common['X-Requested-With'];

        $authProvider.oauth2({
            name: 'instagram',
            url: '/social/instagram',
            redirectUri: 'http://localhost:9000/',
            clientId: '19dd284d24b04e7182b142ede16324b8',
            requiredUrlParams: ['scope'],
            scope: ['likes'],
            scopeDelimiter: '+',
            authorizationEndpoint: 'https://api.instagram.com/oauth/authorize'
        });
        $authProvider.oauth2({
            name: 'vk',
            url: '/social/vk',
            redirectUri: 'http://localhost:9000/',
            clientId: '4929134',
            //requiredUrlParams: ['scope'],
            //scope: ['likes'],
            //scopeDelimiter: '+',
            authorizationEndpoint: 'https://oauth.vk.com/authorize'
        });

    });

    return app;
});
