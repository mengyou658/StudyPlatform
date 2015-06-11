/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('home.sets.routes', []);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('sets',
            {
                url: 'sets/:setId',
                templateUrl: '/assets/partials/sets/index.html',
                controller:controllers.CardsSetCtrl
            }
        );
    }]);
    return mod;
});