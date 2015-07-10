/**
 * Created by m.cherkasov on 11.06.15.
 */
define([], function() {
    'use strict';

    var SidebarCtrl = function($scope, playRoutes, modalService) {
        $scope.courses = [];
        var url = playRoutes.controllers.CoursesController.list();

        url.get().success(function(data) {
            $scope.courses = data;
        });

        var initModal = function() {
            modalService.initAsClasses({
                templateUrl: '/assets/partials/modals/manageClass.html',
                windowClass: 'classes-modal'
            });
        };

        $scope.create = function(){
            initModal();
            modalService.create().result.then(function (data) {

            });
        };


    };

    return {
        SidebarCtrl : SidebarCtrl
    };

});
