/**
 * Created by maximcherkasov on 09.05.15.
 */
define([], function() {
    'use strict';

    var CourseCtrl = function($scope, $http, $state, $stateParams, modalService, playRoutes) {
        $scope.course = {};

        playRoutes.controllers.CoursesController.getCourse($stateParams.courseId).get().success(function(data) {
            $scope.course = data;
        });

        //$scope.query = {};
        //$scope.cards = [];

        //$http.get('/sets/'+$stateParams.setId) .success(function(data) {
        //    $scope.set = data;
        //    $http.get('/sets/'+$stateParams.setId+'/cards').success(function(data) {
        //        $scope.cards = data;
        //    });
        //});
        //
        //$scope.save = function(data, id) {
        //    angular.extend(data, {id: parseInt(id)});
        //
        //    return $http.post('/sets/'+$stateParams.setId, data);
        //};
        //
        //$scope.searchCard = function (row) {
        //    if (!$scope.query.text) return true;
        //
        //    console.log(row);
        //    console.log(row.term);
        //    console.log(angular.lowercase(row.term));
        //
        //    return (angular.lowercase(row.term).indexOf($scope.query.text || '') !== -1
        //            || angular.lowercase(row.transcription).indexOf($scope.query.text || '') !== -1
        //            || angular.lowercase(row.definition).indexOf($scope.query.text || '') !== -1);
        //};
        //
        //modalService.initAsCardsSet({
        //    templateUrl: '/assets/partials/modals/manageCardsSet.html',
        //    windowClass: 'cards-set-modal'
        //});
        //
        //$scope.editCardSet = function(){
        //    modalService.edit($scope.set).result.then(function (data) {
        //        $scope.set.name = data.name;
        //        $scope.set.updated = data.updated;
        //
        //    });
        //};


    };
    CourseCtrl.$inject = [ '$scope', '$http', '$state', '$stateParams', 'modalService', 'playRoutes' ];

    return {
        CourseCtrl : CourseCtrl
    };

});