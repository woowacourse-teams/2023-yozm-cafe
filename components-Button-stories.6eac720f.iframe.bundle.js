"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[706],{"./src/components/Button.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>__WEBPACK_DEFAULT_EXPORT__});const __WEBPACK_DEFAULT_EXPORT__={title:"Button",component:__webpack_require__("./src/components/Button.tsx").Z},Default={args:{children:"버튼",$fullWidth:!0}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    children: '버튼',\n    $fullWidth: true\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]},"./src/components/Button.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Button=({children,$variant="default",$fullWidth=!1,$fullHeight=!1,...rest})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_0__.jsx)(Container,{$variant,$fullWidth,$fullHeight,...rest,children});Button.displayName="Button";const __WEBPACK_DEFAULT_EXPORT__=Button,ButtonVariants={disabled:styled_components__WEBPACK_IMPORTED_MODULE_1__.iv`
    color: ${props=>props.theme.color.white};
    background-color: ${props=>props.theme.color.gray};
    border: none;
  `,outlined:styled_components__WEBPACK_IMPORTED_MODULE_1__.iv`
    color: ${props=>props.theme.color.gray};
    background-color: ${props=>props.theme.color.white};
    border: 2px solid ${props=>props.theme.color.primary};
  `,default:styled_components__WEBPACK_IMPORTED_MODULE_1__.iv`
    color: ${props=>props.theme.color.white};
    background-color: ${props=>props.theme.color.primary};
    border: none;
  `},Container=styled_components__WEBPACK_IMPORTED_MODULE_1__.ZP.button`
  cursor: pointer;

  padding: ${({theme})=>theme.space[1.5]} 0;

  font-size: 16px;
  font-weight: 500;

  border-radius: 40px;
  ${props=>ButtonVariants[props.$variant||"default"]}
  ${props=>props.$fullWidth&&"width: 100%;"}
  ${props=>props.$fullHeight&&"height: 100%;"}
`;try{Button.displayName="Button",Button.__docgenInfo={description:"",displayName:"Button",props:{$variant:{defaultValue:{value:"default"},description:"",name:"$variant",required:!1,type:{name:"enum",value:[{value:'"disabled"'},{value:'"default"'},{value:'"outlined"'}]}},$fullWidth:{defaultValue:{value:"false"},description:"",name:"$fullWidth",required:!1,type:{name:"boolean"}},$fullHeight:{defaultValue:{value:"false"},description:"",name:"$fullHeight",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Button.tsx#Button"]={docgenInfo:Button.__docgenInfo,name:"Button",path:"src/components/Button.tsx#Button"})}catch(__react_docgen_typescript_loader_error){}}}]);
//# sourceMappingURL=components-Button-stories.6eac720f.iframe.bundle.js.map