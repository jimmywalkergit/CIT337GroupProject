(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('orderlog', {
            parent: 'entity',
            url: '/orderlog',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Orderlogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orderlog/orderlogs.html',
                    controller: 'OrderlogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('orderlog-detail', {
            parent: 'entity',
            url: '/orderlog/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Orderlog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orderlog/orderlog-detail.html',
                    controller: 'OrderlogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Orderlog', function($stateParams, Orderlog) {
                    return Orderlog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'orderlog',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('orderlog-detail.edit', {
            parent: 'orderlog-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orderlog/orderlog-dialog.html',
                    controller: 'OrderlogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Orderlog', function(Orderlog) {
                            return Orderlog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orderlog.new', {
            parent: 'orderlog',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orderlog/orderlog-dialog.html',
                    controller: 'OrderlogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('orderlog', null, { reload: 'orderlog' });
                }, function() {
                    $state.go('orderlog');
                });
            }]
        })
        .state('orderlog.edit', {
            parent: 'orderlog',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orderlog/orderlog-dialog.html',
                    controller: 'OrderlogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Orderlog', function(Orderlog) {
                            return Orderlog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orderlog', null, { reload: 'orderlog' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orderlog.delete', {
            parent: 'orderlog',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orderlog/orderlog-delete-dialog.html',
                    controller: 'OrderlogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Orderlog', function(Orderlog) {
                            return Orderlog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orderlog', null, { reload: 'orderlog' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
