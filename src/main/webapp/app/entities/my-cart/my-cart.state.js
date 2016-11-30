(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-cart', {
            parent: 'entity',
            url: '/my-cart',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyCarts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-cart/my-carts.html',
                    controller: 'MyCartController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('my-cart-detail', {
            parent: 'entity',
            url: '/my-cart/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyCart'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-cart/my-cart-detail.html',
                    controller: 'MyCartDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MyCart', function($stateParams, MyCart) {
                    return MyCart.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'my-cart',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('my-cart-detail.edit', {
            parent: 'my-cart-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-cart/my-cart-dialog.html',
                    controller: 'MyCartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyCart', function(MyCart) {
                            return MyCart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-cart.new', {
            parent: 'my-cart',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-cart/my-cart-dialog.html',
                    controller: 'MyCartDialogController',
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
                    $state.go('my-cart', null, { reload: 'my-cart' });
                }, function() {
                    $state.go('my-cart');
                });
            }]
        })
        .state('my-cart.edit', {
            parent: 'my-cart',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-cart/my-cart-dialog.html',
                    controller: 'MyCartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyCart', function(MyCart) {
                            return MyCart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-cart', null, { reload: 'my-cart' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-cart.delete', {
            parent: 'my-cart',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-cart/my-cart-delete-dialog.html',
                    controller: 'MyCartDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MyCart', function(MyCart) {
                            return MyCart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-cart', null, { reload: 'my-cart' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
