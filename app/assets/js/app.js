/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular'
    ,'home'
    ,'home.sets'
    ,'header'
    ,'dashboard'
    ,'common'
    ,'profile'
    ,'services'
    ,'ui-select'
    ], function(angular) {
    'use strict';

    // We must already declare most dependencies here (except for common), or the submodules' routes
    // will not be resolved
    var app = angular.module('app', [
        'rema7.home'
        ,'rema7.home.sets'
        ,'rema7.header'
        ,'rema7.profile'
        ,'rema7.dashboard'
        ,'rema7.common'
        ,'rema7.services'
        ,'ui.select'
        ,'ngSanitize'
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

    }).config(function(uiSelectConfig) {
        uiSelectConfig.theme = 'bootstrap';
        uiSelectConfig.resetSearchInput = true;
        uiSelectConfig.appendToBody = true;
    }).filter('propsFilter', function() {
        return function(items, props) {
            var out = [];

            if (angular.isArray(items)) {
                items.forEach(function(item) {
                    var itemMatches = false;

                    var keys = Object.keys(props);
                    for (var i = 0; i < keys.length; i++) {
                        var prop = keys[i];
                        var text = props[prop].toLowerCase();
                        if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                            itemMatches = true;
                            break;
                        }
                    }

                    if (itemMatches) {
                        out.push(item);
                    }
                });
            } else {
                // Let the output be the input untouched
                out = items;
            }

            return out;
        }
    });

    app.directive('ngEnter', function () {
        return function (scope, element, attrs) {
            element.bind("keydown keypress", function (event) {
                if(event.which === 13) {
                    scope.$apply(function (){
                        scope.$eval(attrs.ngEnter);
                    });

                    event.preventDefault();
                }
            });
        };
    });

    return app;
});
