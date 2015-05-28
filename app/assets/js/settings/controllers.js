/**
 * Created by maximcherkasov on 10.05.15.
 */
define([  ], function() {
    'use strict';

    var SettingsProductsCtrl = function($scope, $modal, $http, $auth, playRoutes, rpcService) {
        $scope.products = {};

        $scope.tabData   = [
            {
                heading: 'Products',
                route:   'settings.products'
            },
            {
                heading: 'FlipCard Lists',
                route:   'settings.flipcards'
            }
        ];

        //var profileUrl = playRoutes.controllers.ApiController.getMethods("rema7.study.cards.Cards");
        //
        //console.log(profileUrl);
        //
        //profileUrl.post(rpcService.getRequest("asd", "create")
        //    ).
        //    success(function(data, status, headers, config) {
        //        //$scope.user.name = data;
        //        console.log(data);
        //    }).
        //    error(function(data, status, headers, config) {
        //        // called asynchronously if an error occurs
        //        // or server returns response with an error status.
        //    });

        $http.get('/product')
            .success(function(data) {
                $scope.products = data;
            });

        var openModal = function(item) {
            if (item === undefined)
                item = {};

            return  $modal.open({
                templateUrl: '/assets/partials/settings/modal.html',
                windowClass: 'manage-product-modal',
                controller: ModalInstanceCtrl,
                resolve: {
                    data: function () {
                        return item;
                    }
                }
            });
        };

        $scope.create = function () {
            openModal().result.then(function (product) {
                $http.post('/product', product)
                    .success(function(data) {
                        $scope.products.push(data);
                    })
            });
        };

        $scope.edit = function (item) {
            openModal(item).result.then(function (product) {
            });
        };

        $scope.remove = function (item) {
            $http.get('/product')
                .success(function(data) {
                    $scope.products = data;
                });
        };
    };

    SettingsProductsCtrl.$inject = [ '$scope', '$modal', '$http', '$auth',  'playRoutes', 'rpcService' ];

    var ModalInstanceCtrl = function ($scope, $modalInstance, data) {
        console.log(data);
        $scope.product = data;

        $scope.save = function () {
            $modalInstance.close($scope.product);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };

    ModalInstanceCtrl.$inject = ['$scope', '$modalInstance', 'data' ];

    return {
        SettingsProductsCtrl : SettingsProductsCtrl
    };

});