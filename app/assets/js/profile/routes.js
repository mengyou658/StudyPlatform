/**
 * Created by maximcherkasov on 10.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('profile.routes', []);
    mod.config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/profile',  {templateUrl: '/assets/partials/profile/index.html',  controller:controllers.ProfileCtrl});
    }]);

    return mod;
});