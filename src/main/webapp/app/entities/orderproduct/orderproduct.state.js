(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('orderproduct', {
            parent: 'entity',
            url: '/orderproduct',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Orderproducts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orderproduct/orderproducts.html',
                    controller: 'OrderproductController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('orderproduct-detail', {
            parent: 'entity',
            url: '/orderproduct/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Orderproduct'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/orderproduct/orderproduct-detail.html',
                    controller: 'OrderproductDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Orderproduct', function($stateParams, Orderproduct) {
                    return Orderproduct.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'orderproduct',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('orderproduct-detail.edit', {
            parent: 'orderproduct-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orderproduct/orderproduct-dialog.html',
                    controller: 'OrderproductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Orderproduct', function(Orderproduct) {
                            return Orderproduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orderproduct.new', {
            parent: 'orderproduct',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orderproduct/orderproduct-dialog.html',
                    controller: 'OrderproductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                price: null,
                                description: null,
                                image: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('orderproduct', null, { reload: 'orderproduct' });
                }, function() {
                    $state.go('orderproduct');
                });
            }]
        })
        .state('orderproduct.edit', {
            parent: 'orderproduct',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orderproduct/orderproduct-dialog.html',
                    controller: 'OrderproductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Orderproduct', function(Orderproduct) {
                            return Orderproduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orderproduct', null, { reload: 'orderproduct' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('orderproduct.delete', {
            parent: 'orderproduct',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/orderproduct/orderproduct-delete-dialog.html',
                    controller: 'OrderproductDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Orderproduct', function(Orderproduct) {
                            return Orderproduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('orderproduct', null, { reload: 'orderproduct' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
