/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', 'home', 'dashboard', 'common', 'profile'], function(angular) {
    'use strict';

    // We must already declare most dependencies here (except for common), or the submodules' routes
    // will not be resolved
    var app = angular.module('app', [
        'rema7.home'
        ,'rema7.profile'
        ,'rema7.dashboard'
        ,'rema7.common']);

    app.config(function ($routeProvider, $httpProvider) {
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
                        console.log('zzzz');
                        $window.location = '/login';
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
        //'responseObserver');
        /* other configuration, like routing */
    });

    return app;
    //angular.module('app', [
    //     'rema7.home'
    //    ,'rema7.profile'
    //    ,'rema7.dashboard'
    //    ,'rema7.common'])
    //    .factory('responseObserver',
    //        function responseObserver($q, $window) {
    //            return function (promise) {
    //                return promise.then(function (successResponse) {
    //                    return successResponse;
    //                }, function (errorResponse) {
    //
    //                    switch (errorResponse.status) {
    //                        case 401:
    //                            console.log('/login');
    //                           // $window.location = $window.location;
    //                            break;
    //                        case 403:
    //                            $window.location = './403.html';
    //                            break;
    //                        case 500:
    //                            $window.location = './500.html';
    //                    }
    //
    //                    return $q.reject(errorResponse);
    //                });
    //            };
    //    })
    //
});
