/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('home.routes', []);
    mod.config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/',  {templateUrl: '/assets/partials/index.html',  controller:controllers.HomeCtrl});
    }]);
    return mod;
});