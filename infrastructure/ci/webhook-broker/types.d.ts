export type WebhookMessage = TCBuildFinishedMessage;

/**
 * @example
 * {
 *   "eventType": "BUILD_FINISHED",
 *   "payload": {
 *     "id": 9,
 *     "buildTypeId": "id2023YozmCafe_YozmCafe_Client_dev",
 *     "number": "3",
 *     "status": "FAILURE",
 *     "state": "finished",
 *     "branchName": "refs/heads/dev",
 *     "defaultBranch": true,
 *     "href": "/app/rest/builds/id:9",
 *     "webUrl": "http://localhost:8111/viewLog.html?buildId=9&buildTypeId=id2023YozmCafe_YozmCafe_Client_dev",
 *     "statusText": "Exit code 1 (Step: 의도적인 실패 (Command Line)) (new)",
 *     "buildType": {
 *       "id": "id2023YozmCafe_YozmCafe_Client_dev",
 *       "name": "Client:dev",
 *       "description": "클라이언트 CI/CD",
 *       "projectName": "2023 Yozm Cafe",
 *       "projectId": "id2023YozmCafe",
 *       "href": "/app/rest/buildTypes/id:id2023YozmCafe_YozmCafe_Client_dev",
 *       "webUrl": "http://localhost:8111/viewType.html?buildTypeId=id2023YozmCafe_YozmCafe_Client_dev"
 *     },
 *     "queuedDate": "20230821T084657+0000",
 *     "startDate": "20230821T084658+0000",
 *     "finishDate": "20230821T084709+0000",
 *     "triggered": {
 *       "type": "user",
 *       "date": "20230821T084657+0000",
 *       "user": {
 *         "username": "2023-yozm-cafe",
 *         "id": 1,
 *         "href": "/app/rest/users/id:1"
 *       }
 *     },
 *     "lastChanges": {
 *       "count": 1,
 *       "change": [
 *         {
 *           "id": 1,
 *           "version": "d1a9f4773431d60bb53d9a9c5476c6813c266775",
 *           "username": "solo_5",
 *           "date": "20230820T230817+0000",
 *           "href": "/app/rest/changes/id:1",
 *           "webUrl": "http://localhost:8111/viewModification.html?modId=1&personal=false"
 *         }
 *       ]
 *     },
 *     "changes": {
 *       "count": 0,
 *       "href": "/app/rest/changes?locator=build:(id:9)"
 *     },
 *     "revisions": {
 *       "count": 1,
 *       "revision": [
 *         {
 *           "version": "d1a9f4773431d60bb53d9a9c5476c6813c266775",
 *           "vcsBranchName": "refs/heads/dev",
 *           "vcs-root-instance": {
 *             "id": "3",
 *             "vcs-root-id": "id2023YozmCafe_DevVcs",
 *             "name": "2023-yozm-cafe dev",
 *             "href": "/app/rest/vcs-root-instances/id:3"
 *           }
 *         }
 *       ]
 *     },
 *     "agent": {
 *       "id": 2,
 *       "name": "Agent 2",
 *       "typeId": 2,
 *       "href": "/app/rest/agents/id:2",
 *       "webUrl": "http://localhost:8111/agentDetails.html?id=2&agentTypeId=2&realAgentName=Agent%202"
 *     },
 *     "problemOccurrences": {
 *       "count": 1,
 *       "href": "/app/rest/problemOccurrences?locator=build:(id:9)",
 *       "newFailed": 1
 *     },
 *     "artifacts": {
 *       "count": 0,
 *       "href": "/app/rest/builds/id:9/artifacts/children/"
 *     },
 *     "relatedIssues": {
 *       "href": "/app/rest/builds/id:9/relatedIssues"
 *     },
 *     "properties": {
 *       "property": [
 *
 *       ],
 *       "count": 6
 *     },
 *     "statistics": {
 *       "href": "/app/rest/builds/id:9/statistics"
 *     },
 *     "vcsLabels": [],
 *     "finishOnAgentDate": "20230821T084709+0000",
 *     "customization": {},
 *     "matrixConfiguration": {
 *       "enabled": false
 *     }
 *   }
 */
type TCBuildFinishedMessage = {
  eventType: "BUILD_FINISHED";
  payload: {
    id: number;
    buildTypeId: string;
    number: string;
    status: "FAILURE" | "SUCCESS";
    state: "running" | "finished";
    branchName: string;
    defaultBranch: boolean;
    href: string;
    webUrl: string;
    statusText: string;
    buildType: {
      id: string;
      name: string;
      description: string;
      projectName: string;
      projectId: string;
      href: string;
      webUrl: string;
    };
    queuedDate: string;
    startDate: string;
    finishDate: string;
    triggered: {
      type: string;
      date: string;
      user: {
        username: string;
        id: number;
        href: string;
      };
    };
    lastChanges: {
      count: number;
      change: Array<{
        id: number;
        version: string;
        username: string;
        date: string;
        href: string;
        webUrl: string;
      }>;
    };
    changes: {
      count: number;
      href: string;
    };
  };
};
