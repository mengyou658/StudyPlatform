/**
 * Created by maximcherkasov on 10.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('profile.routes', []);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('profile', {url: '/profile', templateUrl: '/assets/partials/profile/index.html',  controller:controllers.ProfileCtrl});
    }]);

    return mod;
});