/**
 * Created by maximcherkasov on 09.05.15.
 */

define([], function() {
    'use strict';

    var FlipCardsPackCtrl = function($scope, $http, $stateParams, $modal, DTOptionsBuilder, DTColumnBuilder) {

        $scope.items = {};

        $http.get('/flip_cards/packs/'+$stateParams.packId)
            .success(function(data) {
                $scope.pack = data;
            });


        $scope.dtOptions = DTOptionsBuilder.fromSource('/flip_cards/packs/'+$stateParams.packId)
            .withPaginationType('full_numbers');
        $scope.dtColumns = [
            DTColumnBuilder.newColumn('original').withTitle('Original'),
            DTColumnBuilder.newColumn('transcription').withTitle('Transcription'),
            DTColumnBuilder.newColumn('translation').withTitle('Translation')
        ];



        var openModal = function(item) {
            if (item === undefined)
                item = {};

            return  $modal.open({
                templateUrl: '/assets/partials/flipCards/pack/modal.html',
                windowClass: 'manage-cards-modal',
                controller: ModalInstanceCtrl,
                resolve: {
                    data: function () {
                        return {
                            pack: $scope.pack,
                            item: item
                        };
                    }
                }
            });
        };

        $scope.addFlashCard = function () {
            openModal().result.then(function (data) {
                 $scope.items.push(data);
            });
        };

        $scope.edit = function (item) {
            openModal(item).result.then(function (data) {
            });
        };

        $scope.remove = function (item) {
            //$http.get('/product')
            //    .success(function(data) {
            //        $scope.items = data;
            //    });
        };
    };

    FlipCardsPackCtrl.$inject = [ '$scope', '$http', '$stateParams', '$modal', 'DTOptionsBuilder', 'DTColumnBuilder' ];


    var ModalInstanceCtrl = function ($scope, $http, $modalInstance, data) {
        console.log(data);
        $scope.pack = data.pack;
        $scope.item = data.item;


        $scope.create = function () {
            $http.post('/flip_cards/packs/'+$scope.pack.id+'/flip_cards', $scope.item)
                .success(function(data) {
                    $modalInstance.close(data);
                }).error(function(data, status, headers, config) {
                    console.log(data)
                });
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
    ModalInstanceCtrl.$inject = ['$scope', '$http', '$modalInstance', 'data' ];

    return {
        FlipCardsPackCtrl : FlipCardsPackCtrl
    };

});