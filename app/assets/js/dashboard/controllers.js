/**
 * Created by maximcherkasov on 09.05.15.
 */

define([], function() {
    'use strict';

    var DashboardCtrl = function($scope, $http, $auth) {
        $scope.user = "";
        $scope.accounts = [];
        $scope.feed = [];

        $http.get('/social')
            .success(function(data) {
                $scope.accounts = data
            });

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

        $scope.feed = function(accessToken) {
            var url = "https://api.instagram.com/v1/users/self/feed?access_token="+accessToken+"&callback=JSON_CALLBACK";
            $http.jsonp(url).success(function(response) {
                console.log(response.data);
                $scope.feed = response.data;
                //callback(response.data);
            })
        }
    };
    DashboardCtrl.$inject = [ '$scope', '$http', '$auth' ];

    return {
        DashboardCtrl : DashboardCtrl
    };

});