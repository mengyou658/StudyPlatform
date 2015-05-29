/**
 * Created by maximcherkasov on 09.05.15.
 */
define(['angular', './controllers', 'common'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('flashCards.routes', ['rema7.common']);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('flashcards', {
                url: '/flashcards',
                templateUrl: '/assets/partials/flash_cards/index.html',
                controller:controllers.FlashCardsCtrl
            })
    }]);
    return mod;
});