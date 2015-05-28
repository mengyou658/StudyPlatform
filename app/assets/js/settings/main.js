/**
 * Created by maximcherkasov on 10.05.15.
 */


define(['angular', './routes', 'angular-ui', 'angular-ui-tpls',
    'angular-ui-router'
    ,'angular-ui-router-tabs'
    ], function(angular) {
    'use strict';

    return angular.module('rema7.settings.products', ['ui.router',
        'settings.products.routes', 'ui.bootstrap', 'ui.router.tabs']);
});