/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular'
    ,'home'
    ,'home.sets'
    ,'header'
    ,'dashboard'
    ,'common'
    ,'profile'
    ,'services'
    ,'ui-select'
    ], function(angular) {
    'use strict';

    // We must already declare most dependencies here (except for common), or the submodules' routes
    // will not be resolved
    var app = angular.module('app', [
        'rema7.home'
        ,'rema7.home.sets'
        ,'rema7.header'
        ,'rema7.profile'
        ,'rema7.dashboard'
        ,'rema7.common'
        ,'rema7.services'
        ,'ui.select'
        ,'ngSanitize'
        ]);

    app.config(function ($stateProvider, $urlRouterProvider, $httpProvider, $authProvider) {
        $httpProvider.interceptors.push(function($q, $window) {
            return {
                request: function(request) {
                    //request.headers.authorization =
                    //    userService.getAuthorization();
                    return request;
                },
                // This is the responseError interceptor
                responseError: function(rejection) {
                    if (rejection.status === 401) {
                        // Return a new promise

                        $window.location = '/';
                        //return userService.authenticate().then(function() {
                        //    return $injector.get('$http')(rejection.config);
                        //});
                    }

                    /* If not a 401, do nothing with this error.
                     * This is necessary to make a `responseError`
                     * interceptor a no-op. */
                    return $q.reject(rejection);
                }
            };


        });

        $urlRouterProvider.otherwise("/dashboard");
        //$httpProvider.defaults.useXDomain = true;
        //delete $httpProvider.defaults.headers.common['X-Requested-With'];

        $authProvider.oauth2({
            name: 'instagram',
            url: '/social/instagram',
            redirectUri: 'http://localhost:9000/',
            clientId: '19dd284d24b04e7182b142ede16324b8',
            requiredUrlParams: ['scope'],
            scope: ['likes'],
            scopeDelimiter: '+',
            authorizationEndpoint: 'https://api.instagram.com/oauth/authorize'
        });
        $authProvider.oauth2({
            name: 'vk',
            url: '/social/vk',
            redirectUri: 'http://localhost:9000/',
            clientId: '4929134',
            //requiredUrlParams: ['scope'],
            //scope: ['likes'],
            //scopeDelimiter: '+',
            authorizationEndpoint: 'https://oauth.vk.com/authorize'
        });

    }).config(function(uiSelectConfig) {
        uiSelectConfig.theme = 'bootstrap';
        uiSelectConfig.resetSearchInput = true;
        uiSelectConfig.appendToBody = true;
    }).filter('propsFilter', function() {
        return function(items, props) {
            var out = [];

            if (angular.isArray(items)) {
                items.forEach(function(item) {
                    var itemMatches = false;

                    var keys = Object.keys(props);
                    for (var i = 0; i < keys.length; i++) {
                        var prop = keys[i];
                        var text = props[prop].toLowerCase();
                        if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                            itemMatches = true;
                            break;
                        }
                    }

                    if (itemMatches) {
                        out.push(item);
                    }
                });
            } else {
                // Let the output be the input untouched
                out = items;
            }

            return out;
        }
    });

    app.directive('ngEnter', function () {
        return function (scope, element, attrs) {
            element.bind("keydown keypress", function (event) {
                if(event.which === 13) {
                    scope.$apply(function (){
                        scope.$eval(attrs.ngEnter);
                    });

                    event.preventDefault();
                }
            });
        };
    });

    app.filter('pinyin', function() {

        function getUpperCaseIndices(str) {
            var indices = [];
            for(var i = 0; i < str.length; i++) {
                if(str[i] === str[i].toUpperCase()) {
                    indices.push(i);
                }
            }
            return indices;
        }

        function revertToUpperCase(str, indices) {
            var chars = str.split('');
            indices.map(function(idx) {
                chars[idx] = chars[idx].toUpperCase();
            });
            return chars.join('');
        }

        return function(text) {
            var tonePtn = /([aeiouvüAEIOUVÜ]{1,2}(n|ng|r|\'er|N|NG|R|\'ER){0,1}[1234])/g;
            var toneMap = {
                a: ['ā', 'á', 'ǎ', 'à'],
                ai: ['āi', 'ái', 'ǎi', 'ài'],
                ao: ['āo', 'áo', 'ǎo', 'ào'],
                e: ['ē', 'é', 'ě', 'è'],
                ei: ['ēi', 'éi', 'ěi', 'èi'],
                i: ['ī', 'í', 'ǐ', 'ì'],
                ia: ['iā', 'iá', 'iǎ', 'ià'],
                ie: ['iē', 'ié', 'iě', 'iè'],
                io: ['iō', 'ió', 'iǒ', 'iò'],
                iu: ['iū', 'iú', 'iǔ', 'iù'],
                o: ['ō', 'ó', 'ǒ', 'ò'],
                ou: ['ōu', 'óu', 'ǒu', 'òu'],
                u: ['ū', 'ú', 'ǔ', 'ù'],
                ua: ['uā', 'uá', 'uǎ', 'uà'],
                ue: ['uē', 'ué', 'uě', 'uè'],
                ui: ['uī', 'uí', 'uǐ', 'uì'],
                uo: ['uō', 'uó', 'uǒ', 'uò'],
                v: ['ǖ', 'ǘ', 'ǚ', 'ǜ'],
                ve: ['üē', 'üé', 'üě', 'üè'],
                ü: ['ǖ', 'ǘ', 'ǚ', 'ǜ'],
                üe: ['üē', 'üé', 'üě', 'üè'],
            };
            var tones = text.match(tonePtn);
            if (tones) {
                tones.forEach(function(coda, idx, arr) {
                    var toneIdx = parseInt(coda.slice(-1)) - 1;
                    var vowel = coda.slice(0, -1);
                    var suffix = vowel.match(/(n|ng|r|\'er|N|NG|R|\'ER)$/);
                    vowel = vowel.replace(/(n|ng|r|\'er|N|NG|R|\'ER)$/, '');
                    var upperCaseIdxs = getUpperCaseIndices(vowel);
                    vowel = vowel.toLowerCase();
                    var replacement = suffix && toneMap[vowel][toneIdx] + suffix[0] || toneMap[vowel][toneIdx];
                    text = text.replace(coda, revertToUpperCase(replacement, upperCaseIdxs));
                });
            }
            return text;
        };
    });

    app.directive('ngPinyin', ['$timeout', 'pinyinFilter', function($timeout, pinyinFilter){

        function link(scope, element, attrs, ngModelCtrl) {

            function convertToPinyin(text) {
                var updatedText = text;
                if (text && typeof text !== "string") {
                    throw Error("ng-pinyin: ng-model needs to refer to a string!");
                } else if (text) {
                    updatedText = pinyinFilter(text);
                }
                if(updatedText !== text) {
                    ngModelCtrl.$setViewValue(updatedText);
                    ngModelCtrl.$render();
                }
                return text;
            }

            var modelValue = scope[attrs.ngModel];
            if (modelValue && typeof modelValue !== "string") {
                throw Error("ng-pinyin: ng-model needs to refer to a string!");
            }

            ngModelCtrl.$parsers.push(convertToPinyin);
        }

        return {
            require: 'ngModel',
            restrict: 'A',
            link: link
        };
    }]);

    return app;
});
