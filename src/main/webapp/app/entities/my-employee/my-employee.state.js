(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-employee', {
            parent: 'entity',
            url: '/my-employee',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyEmployees'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-employee/my-employees.html',
                    controller: 'MyEmployeeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('my-employee-detail', {
            parent: 'entity',
            url: '/my-employee/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyEmployee'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-employee/my-employee-detail.html',
                    controller: 'MyEmployeeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MyEmployee', function($stateParams, MyEmployee) {
                    return MyEmployee.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'my-employee',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('my-employee-detail.edit', {
            parent: 'my-employee-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-employee/my-employee-dialog.html',
                    controller: 'MyEmployeeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyEmployee', function(MyEmployee) {
                            return MyEmployee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-employee.new', {
            parent: 'my-employee',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-employee/my-employee-dialog.html',
                    controller: 'MyEmployeeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                email: null,
                                phoneNumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('my-employee', null, { reload: 'my-employee' });
                }, function() {
                    $state.go('my-employee');
                });
            }]
        })
        .state('my-employee.edit', {
            parent: 'my-employee',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-employee/my-employee-dialog.html',
                    controller: 'MyEmployeeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyEmployee', function(MyEmployee) {
                            return MyEmployee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-employee', null, { reload: 'my-employee' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-employee.delete', {
            parent: 'my-employee',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-employee/my-employee-delete-dialog.html',
                    controller: 'MyEmployeeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MyEmployee', function(MyEmployee) {
                            return MyEmployee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-employee', null, { reload: 'my-employee' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
