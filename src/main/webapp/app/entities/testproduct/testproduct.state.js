(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('testproduct', {
            parent: 'entity',
            url: '/testproduct',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Testproducts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/testproduct/testproducts.html',
                    controller: 'TestproductController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('testproduct-detail', {
            parent: 'entity',
            url: '/testproduct/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Testproduct'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/testproduct/testproduct-detail.html',
                    controller: 'TestproductDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Testproduct', function($stateParams, Testproduct) {
                    return Testproduct.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'testproduct',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('testproduct-detail.edit', {
            parent: 'testproduct-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/testproduct/testproduct-dialog.html',
                    controller: 'TestproductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Testproduct', function(Testproduct) {
                            return Testproduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('testproduct.new', {
            parent: 'testproduct',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/testproduct/testproduct-dialog.html',
                    controller: 'TestproductDialogController',
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
                    $state.go('testproduct', null, { reload: 'testproduct' });
                }, function() {
                    $state.go('testproduct');
                });
            }]
        })
        .state('testproduct.edit', {
            parent: 'testproduct',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/testproduct/testproduct-dialog.html',
                    controller: 'TestproductDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Testproduct', function(Testproduct) {
                            return Testproduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('testproduct', null, { reload: 'testproduct' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('testproduct.delete', {
            parent: 'testproduct',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/testproduct/testproduct-delete-dialog.html',
                    controller: 'TestproductDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Testproduct', function(Testproduct) {
                            return Testproduct.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('testproduct', null, { reload: 'testproduct' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
