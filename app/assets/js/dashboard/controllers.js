/**
 * Created by maximcherkasov on 09.05.15.
 */

define([], function() {
    'use strict';

    var DashboardCtrl = function($scope, $http, $auth) {
        $scope.user = "";
        $scope.accounts = [];
        $scope.feed = [];
        $scope.searchTags = "видное";
        $scope.tagsList = {};

        var callbackName = function() {
            //return 'rema7' + Math.round(new Date().getTime() / 1000) + Math.floor(Math.random() * 100)
            return 'JSON_CALLBACK';
        };

        var loadSocialAccount = function() {
            $http.get('/social')
                .success(function(data) {
                    $scope.accounts = data;
                    $scope.accounts.info = {};

                    $scope.accounts.forEach(function(entry) {
                        console.log(entry);
                        if (entry.provider === 'instagram') {
                            var url = 'https://api.instagram.com/v1/users/'+entry.accountId+'/?access_token='
                                +entry.accessToken+'&callback='+callbackName();
                            $http.jsonp(url).success(function(response) {
                                //console.log(response.data);
                                entry.info = response.data.counts;
                            })
                        }
                        else if (entry.provider === 'vk') {
                            url = 'https://api.vk.com/method/users.get?'+entry.accountId+'&fields=photo_50'+'&access_token='
                                +entry.accessToken+'&callback='+callbackName();
                            $http.jsonp(url).success(function(response) {
                                console.log(response.response);
                                entry.profilePicture = response.response[0].photo_50;
                                entry.username  = response.response[0].first_name + ' ' + response.response[0].last_name;
                            });
                            //https://api.vk.com/method/users.get?user_id=20428768&fields=photo_50&access_token=df9d1584dd4996377dd6901377a83a3d87487a0eaf6456a5ae70159d293cb9450a5f96a144fbbc1328019
                        }
                    });

                    if ($scope.accounts.length > 0)
                        $scope.selectedAccount = $scope.accounts[0];

                });
        };

        loadSocialAccount();

        $scope.instagramLogout = function() {
          $http.jsonp('https://instagram.com/accounts/logout/').success(function(data){
              console.log('success');
          })
        };

        $scope.linkInstagram = function() {
            $auth.link('instagram')
                .then(function(response) {
                    console.log(JSON.stringify(response.data));
                    //$scope.user = JSON.stringify(response.data.user);
                    //$window.localStorage.currentUser = JSON.stringify(response.data.user);
                    //$rootScope.currentUser = JSON.parse($window.localStorage.currentUser);
                    //$http.jsonp('https://instagram.com/accounts/logout/')
                    loadSocialAccount();
                });
        };

        $scope.linkVk = function() {
            $auth.link('vk')
                .then(function(response) {
                    console.log(JSON.stringify(response.data));
                    //$scope.user = JSON.stringify(response.data.user);
                    //$window.localStorage.currentUser = JSON.stringify(response.data.user);
                    //$rootScope.currentUser = JSON.parse($window.localStorage.currentUser);
                    //$http.jsonp('https://instagram.com/accounts/logout/')
                    loadSocialAccount();
                });
        };

        $scope.feed = function(accessToken) {

            var url = 'https://api.instagram.com/v1/users/self/feed?access_token='+accessToken+'&callback='+callbackName();
            $http.jsonp(url).success(function(response) {
                //console.log(response.data);
                $scope.feed = response.data;
                //callback(response.data);
            })
        };

        $scope.selectAccount = function(account) {
            $scope.selectedAccount = account;
            console.log($scope.selectedAccount)
        };

        $scope.find = function() {
            //$scope.selectedAccount = account;
            console.log($scope.selectedAccount);
            console.log($scope.searchTags);
            var url = 'https://api.instagram.com/v1/tags/search?q='+$scope.searchTags+
                '&access_token='+$scope.selectedAccount.accessToken+'&callback='+callbackName();
            console.log(url);
            $http.jsonp(url).success(function(response) {
                console.log(response.data);
                $scope.tagsList = response.data;
                //callback(response.data);
            })
        };
    };
    DashboardCtrl.$inject = [ '$scope', '$http', '$auth' ];

    return {
        DashboardCtrl : DashboardCtrl
    };

});