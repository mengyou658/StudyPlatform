/**
 * Created by maximcherkasov on 09.05.15.
 */
define([], function() {
    'use strict';

    var CardsSetCtrl = function($scope, $http) {
        $scope.sets = [];

        $http.get('/sets') .success(function(data) {
            $scope.sets = data;
        });
    };
    CardsSetCtrl.$inject = [ '$scope', '$http' ];

    return {
        CardsSetCtrl : CardsSetCtrl
    };

});