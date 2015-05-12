/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', 'home', 'dashboard', 'common', 'profile'], function(angular) {
    'use strict';

    // We must already declare most dependencies here (except for common), or the submodules' routes
    // will not be resolved
    return angular.module('app', [
         'rema7.home'
        ,'rema7.profile'
        ,'rema7.dashboard'
        ,'rema7.common']);
});
