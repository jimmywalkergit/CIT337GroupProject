(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-orders', {
            parent: 'entity',
            url: '/my-orders',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyOrders'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-orders/my-orders.html',
                    controller: 'MyOrdersController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('my-orders-detail', {
            parent: 'entity',
            url: '/my-orders/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyOrders'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-orders/my-orders-detail.html',
                    controller: 'MyOrdersDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MyOrders', function($stateParams, MyOrders) {
                    return MyOrders.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'my-orders',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('my-orders-detail.edit', {
            parent: 'my-orders-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-orders/my-orders-dialog.html',
                    controller: 'MyOrdersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyOrders', function(MyOrders) {
                            return MyOrders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-orders.new', {
            parent: 'my-orders',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-orders/my-orders-dialog.html',
                    controller: 'MyOrdersDialogController',
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
                    $state.go('my-orders', null, { reload: 'my-orders' });
                }, function() {
                    $state.go('my-orders');
                });
            }]
        })
        .state('my-orders.edit', {
            parent: 'my-orders',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-orders/my-orders-dialog.html',
                    controller: 'MyOrdersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyOrders', function(MyOrders) {
                            return MyOrders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-orders', null, { reload: 'my-orders' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-orders.delete', {
            parent: 'my-orders',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-orders/my-orders-delete-dialog.html',
                    controller: 'MyOrdersDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MyOrders', function(MyOrders) {
                            return MyOrders.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-orders', null, { reload: 'my-orders' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
