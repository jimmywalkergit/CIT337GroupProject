(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('testorder', {
            parent: 'entity',
            url: '/testorder',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Testorders'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/testorder/testorders.html',
                    controller: 'TestorderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('testorder-detail', {
            parent: 'entity',
            url: '/testorder/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Testorder'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/testorder/testorder-detail.html',
                    controller: 'TestorderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Testorder', function($stateParams, Testorder) {
                    return Testorder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'testorder',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('testorder-detail.edit', {
            parent: 'testorder-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/testorder/testorder-dialog.html',
                    controller: 'TestorderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Testorder', function(Testorder) {
                            return Testorder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('testorder.new', {
            parent: 'testorder',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/testorder/testorder-dialog.html',
                    controller: 'TestorderDialogController',
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
                    $state.go('testorder', null, { reload: 'testorder' });
                }, function() {
                    $state.go('testorder');
                });
            }]
        })
        .state('testorder.edit', {
            parent: 'testorder',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/testorder/testorder-dialog.html',
                    controller: 'TestorderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Testorder', function(Testorder) {
                            return Testorder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('testorder', null, { reload: 'testorder' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('testorder.delete', {
            parent: 'testorder',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/testorder/testorder-delete-dialog.html',
                    controller: 'TestorderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Testorder', function(Testorder) {
                            return Testorder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('testorder', null, { reload: 'testorder' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
