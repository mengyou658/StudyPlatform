/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('courses.routes', []);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('courses',
            {
                url: '/courses',
                templateUrl: '/assets/partials/courses/index.html',
                controller:controllers.CoursesCtrl,
                ncyBreadcrumb: {
                    label: 'Courses'
                }
            });
    }]);
    return mod;
});