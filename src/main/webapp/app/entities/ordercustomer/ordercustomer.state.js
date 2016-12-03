(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ordercustomer', {
            parent: 'entity',
            url: '/ordercustomer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ordercustomers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ordercustomer/ordercustomers.html',
                    controller: 'OrdercustomerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('ordercustomer-detail', {
            parent: 'entity',
            url: '/ordercustomer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ordercustomer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ordercustomer/ordercustomer-detail.html',
                    controller: 'OrdercustomerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Ordercustomer', function($stateParams, Ordercustomer) {
                    return Ordercustomer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ordercustomer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ordercustomer-detail.edit', {
            parent: 'ordercustomer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordercustomer/ordercustomer-dialog.html',
                    controller: 'OrdercustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ordercustomer', function(Ordercustomer) {
                            return Ordercustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ordercustomer.new', {
            parent: 'ordercustomer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordercustomer/ordercustomer-dialog.html',
                    controller: 'OrdercustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                address: null,
                                phone: null,
                                email: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ordercustomer', null, { reload: 'ordercustomer' });
                }, function() {
                    $state.go('ordercustomer');
                });
            }]
        })
        .state('ordercustomer.edit', {
            parent: 'ordercustomer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordercustomer/ordercustomer-dialog.html',
                    controller: 'OrdercustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ordercustomer', function(Ordercustomer) {
                            return Ordercustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ordercustomer', null, { reload: 'ordercustomer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ordercustomer.delete', {
            parent: 'ordercustomer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ordercustomer/ordercustomer-delete-dialog.html',
                    controller: 'OrdercustomerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ordercustomer', function(Ordercustomer) {
                            return Ordercustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ordercustomer', null, { reload: 'ordercustomer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
