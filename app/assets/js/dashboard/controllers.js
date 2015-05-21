/**
 * Created by maximcherkasov on 09.05.15.
 */

define([], function() {
    'use strict';

    var DashboardCtrl = function($scope, $http, $auth) {
        $scope.user = "";
        $scope.nodes = [];

        $scope.linkInstagram = function() {
            $auth.link('instagram')
                .then(function(response) {
                    console.log(JSON.stringify(response.data));
                    //$scope.user = JSON.stringify(response.data.user);
                    //$window.localStorage.currentUser = JSON.stringify(response.data.user);
                    //$rootScope.currentUser = JSON.parse($window.localStorage.currentUser);
                    //$http.jsonp('https://instagram.com/accounts/logout/')
                });
        };
    };
    DashboardCtrl.$inject = [ '$scope', '$http', '$auth' ];

    return {
        DashboardCtrl : DashboardCtrl
    };

});