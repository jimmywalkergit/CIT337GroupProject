(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-customer', {
            parent: 'entity',
            url: '/my-customer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyCustomers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-customer/my-customers.html',
                    controller: 'MyCustomerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('my-customer-detail', {
            parent: 'entity',
            url: '/my-customer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyCustomer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-customer/my-customer-detail.html',
                    controller: 'MyCustomerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MyCustomer', function($stateParams, MyCustomer) {
                    return MyCustomer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'my-customer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('my-customer-detail.edit', {
            parent: 'my-customer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-customer/my-customer-dialog.html',
                    controller: 'MyCustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyCustomer', function(MyCustomer) {
                            return MyCustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-customer.new', {
            parent: 'my-customer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-customer/my-customer-dialog.html',
                    controller: 'MyCustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                phone: null,
                                email: null,
                                address: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('my-customer', null, { reload: 'my-customer' });
                }, function() {
                    $state.go('my-customer');
                });
            }]
        })
        .state('my-customer.edit', {
            parent: 'my-customer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-customer/my-customer-dialog.html',
                    controller: 'MyCustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyCustomer', function(MyCustomer) {
                            return MyCustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-customer', null, { reload: 'my-customer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-customer.delete', {
            parent: 'my-customer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-customer/my-customer-delete-dialog.html',
                    controller: 'MyCustomerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MyCustomer', function(MyCustomer) {
                            return MyCustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-customer', null, { reload: 'my-customer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
