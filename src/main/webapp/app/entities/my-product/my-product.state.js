(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-product', {
            parent: 'entity',
            url: '/my-product',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyProducts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-product/my-products.html',
                    controller: 'MyProductController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('my-product-detail', {
            parent: 'entity',
            url: '/my-product/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyProduct'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-product/my-product-detail.html',
                    controller: 'MyProductDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MyProduct', function($stateParams, MyProduct) {
                    return MyProduct.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'my-product',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('my-product-detail.edit', {
            parent: 'my-product-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-product/my-product-dialog.html',
                    controller: 'MyProductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyProduct', function(MyProduct) {
                            return MyProduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-product.new', {
            parent: 'my-product',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-product/my-product-dialog.html',
                    controller: 'MyProductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                price: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('my-product', null, { reload: 'my-product' });
                }, function() {
                    $state.go('my-product');
                });
            }]
        })
        .state('my-product.edit', {
            parent: 'my-product',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-product/my-product-dialog.html',
                    controller: 'MyProductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyProduct', function(MyProduct) {
                            return MyProduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-product', null, { reload: 'my-product' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-product.delete', {
            parent: 'my-product',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-product/my-product-delete-dialog.html',
                    controller: 'MyProductDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MyProduct', function(MyProduct) {
                            return MyProduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-product', null, { reload: 'my-product' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
