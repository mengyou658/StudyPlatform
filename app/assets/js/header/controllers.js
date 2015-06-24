/**
 * Created by m.cherkasov on 11.06.15.
 */
define([], function() {
    'use strict';

    var HeaderCtrl = function($scope, $modal, modalService) {
        var initModal = function() {
            modalService.initAsCardsSet({
                templateUrl: '/assets/partials/modals/manageCardsSet.html',
                windowClass: 'cards-set-modal'
            });
        };

        initModal();

        $scope.createCardSet = function(){
            initModal();
            modalService.create().result.then(function (data) {

            });
        };
    };

    HeaderCtrl.$inject = [ '$scope', '$modal', 'modalService' ];

    return {
        HeaderCtrl : HeaderCtrl
    };

});