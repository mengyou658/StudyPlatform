/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes', 'satellizer'], function(angular) {
    'use strict';

    return angular.module('rema7.dashboard', ['ui.router', 'dashboard.routes', 'satellizer']);
});