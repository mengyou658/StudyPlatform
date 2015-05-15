/**
 * Created by maximcherkasov on 10.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('auth.routes', []);
    mod.config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/login',  {templateUrl: '/assets/partials/auth/index.html',  controller:controllers.AuthCtrl});
    }]);

    return mod;
});