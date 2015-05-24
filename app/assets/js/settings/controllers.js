/**
 * Created by maximcherkasov on 10.05.15.
 */
define([  ], function() {
    'use strict';

    var SettingsProductsCtrl = function($scope, $modal, $http, $auth) {

        $scope.products = {};

        $http.get('/product')
            .success(function(data) {
                $scope.products = data;
            });

        $scope.open = function (size) {

            var modalInstance = $modal.open({
                templateUrl: '/assets/partials/settings/modal.html',
                windowClass: 'manage-product-modal',
                controller: ModalInstanceCtrl,
                size: size,
                resolve: {
                    data: function () {
                        return {
                            name: "text"
                        };
                    }
                }
            });

            modalInstance.result.then(function (product) {
                console.log(product);
                $http.post('/product', product)
                    .success(function(data) {

                    })
            }, function () {
                //$log.info('Modal dismissed at: ' + new Date());
            });
        };
    };

    SettingsProductsCtrl.$inject = [ '$scope', '$modal', '$http', '$auth' ];

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