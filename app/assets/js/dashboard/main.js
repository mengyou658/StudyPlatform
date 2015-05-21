/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes'], function(angular) {
    'use strict';

    return angular.module('rema7.dashboard', ['ngRoute', 'dashboard.routes', 'satellizer']);
});