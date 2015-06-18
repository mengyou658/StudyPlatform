/**
 * Created by maximcherkasov on 09.05.15.
 */
define([], function() {
    'use strict';

    var HomeCtrl = function($scope, $http) {
        $scope.sets = [];

        $http.get('/sets') .success(function(data) {
            $scope.sets = data;
        });


    };
    HomeCtrl.$inject = [ '$scope', '$http' ];

    return {
        HomeCtrl : HomeCtrl
    };

});