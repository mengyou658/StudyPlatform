/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', 'home', 'dashboard', 'common', 'profile'], function(angular) {
    'use strict';

    // We must already declare most dependencies here (except for common), or the submodules' routes
    // will not be resolved
    return angular.module('app', [
         'rema7.home'
        ,'rema7.profile'
        ,'rema7.dashboard'
        ,'rema7.common'])
        .factory('responseObserver',
            function responseObserver($q, $window) {
                return function (promise) {
                    return promise.then(function (successResponse) {
                        return successResponse;
                    }, function (errorResponse) {

                        switch (errorResponse.status) {
                            case 401:
                                console.log('/login');
                               // $window.location = $window.location;
                                break;
                            case 403:
                                $window.location = './403.html';
                                break;
                            case 500:
                                $window.location = './500.html';
                        }

                        return $q.reject(errorResponse);
                    });
                };
        })
        .config(function ($routeProvider, $httpProvider) {
            console.log('!!!!');
          $httpProvider.interceptors.push('responseObserver');
        /* other configuration, like routing */
        });
});
