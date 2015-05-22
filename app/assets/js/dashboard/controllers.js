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
                $scope.accounts = data;
                $scope.accounts.info = {};

                $scope.accounts.forEach(function(entry) {
                    //console.log(entry);
                    var url = 'https://api.instagram.com/v1/users/'+entry.accountId+'/?access_token='+entry.accessToken+'&callback=JSON_CALLBACK';
                    $http.jsonp(url).success(function(response) {
                        //console.log(response.data);
                        entry.info = response.data.counts;
                    })
                });


            });

        $scope.feed = function(accessToken) {
            var url = "https://api.instagram.com/v1/users/self/feed?access_token="+accessToken+"&callback=JSON_CALLBACK";
            $http.jsonp(url).success(function(response) {
                //console.log(response.data);
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