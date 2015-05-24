/**
 * Created by maximcherkasov on 10.05.15.
 */


define(['angular', './routes', 'angular-ui', 'angular-ui-tpls'], function(angular) {
    'use strict';

    return angular.module('rema7.settings.products', ['ngRoute', 'settings.products.routes', 'ui.bootstrap']);
});