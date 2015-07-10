/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('courses.course.routes', []);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('courses.course',
            {
                url: '/:courseId',
                templateUrl: '/assets/partials/courses/course/index.html',
                controller:controllers.CourseCtrl,
                ncyBreadcrumb: {
                    label: 'Course {{course.name}}'
                }
            });
    }]);
    return mod;
});