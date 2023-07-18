"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[706],{"./src/components/Button.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>Button_stories});var styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const Button=({children,variant="default",fullWidth=!1,...rest})=>(0,jsx_runtime.jsx)(Container,{variant,fullWidth,...rest,children});Button.displayName="Button";const components_Button=Button,ButtonVariants={disabled:styled_components_browser_esm.iv`
    color: ${props=>props.theme.color.white};
    background-color: ${props=>props.theme.color.gray};
    border: none;
  `,outlined:styled_components_browser_esm.iv`
    color: ${props=>props.theme.color.gray};
    background-color: ${props=>props.theme.color.white};
    border: 2px solid ${props=>props.theme.color.primary};
  `,default:styled_components_browser_esm.iv`
    color: ${props=>props.theme.color.white};
    background-color: ${props=>props.theme.color.primary};
    border: none;
  `},Container=styled_components_browser_esm.ZP.button`
  cursor: pointer;

  padding: ${({theme})=>theme.space[1.5]} 0;

  font-size: 16px;
  font-weight: 500;

  border-radius: 40px;
  ${props=>ButtonVariants[props.variant||"default"]}
  ${props=>props.fullWidth&&"width: 100%;"}
`;try{Button.displayName="Button",Button.__docgenInfo={description:"",displayName:"Button",props:{variant:{defaultValue:{value:"default"},description:"",name:"variant",required:!1,type:{name:"enum",value:[{value:'"disabled"'},{value:'"default"'},{value:'"outlined"'}]}},fullWidth:{defaultValue:{value:"false"},description:"",name:"fullWidth",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/Button.tsx#Button"]={docgenInfo:Button.__docgenInfo,name:"Button",path:"src/components/Button.tsx#Button"})}catch(__react_docgen_typescript_loader_error){}const Button_stories={title:"Button",component:components_Button},Default={args:{}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {}\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-Button-stories.ac8fb7b7.iframe.bundle.js.map