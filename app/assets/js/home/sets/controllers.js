/**
 * Created by maximcherkasov on 09.05.15.
 */
define([], function() {
    'use strict';

    var CardsSetCtrl = function($scope, $http, $stateParams) {
        $scope.set = {};

        $http.get('/sets/'+$stateParams.setId) .success(function(data) {
            $scope.set = data;
        });
    };
    CardsSetCtrl.$inject = [ '$scope', '$http', '$stateParams' ];


    var CardsSetEditCtrl = function($scope, $http, $stateParams) {
        $scope.set = {};

        console.log('asd');

        $http.get('/sets/'+$stateParams.setId) .success(function(data) {
            $scope.set = data;
        });
    };
    CardsSetEditCtrl.$inject = [ '$scope', '$http', '$stateParams' ];

    return {
        CardsSetCtrl : CardsSetCtrl,
        CardsSetEditCtrl : CardsSetEditCtrl
    };

});