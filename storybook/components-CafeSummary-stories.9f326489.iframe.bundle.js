"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[53],{"./src/components/CafeSummary.stories.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:function(){return Default},__namedExportsOrder:function(){return __namedExportsOrder},default:function(){return CafeSummary_stories}});var index_esm=__webpack_require__("./node_modules/react-icons/sl/index.esm.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const CafeSummary=props=>{const{title:title,address:address,onClick:onClick}=props;return(0,jsx_runtime.jsx)(Container,{onClick:onClick,role:"button",tabIndex:0,children:(0,jsx_runtime.jsxs)(Summary,{children:[(0,jsx_runtime.jsx)(Title,{children:title}),(0,jsx_runtime.jsxs)(Address,{children:[(0,jsx_runtime.jsx)(LocationPinIcon,{}),address]})]})})};CafeSummary.displayName="CafeSummary";const Container=styled_components_browser_esm.zo.div`
  cursor: pointer;
  display: flex;
  width: 100%;
  padding: ${({theme:theme})=>theme.space[3]};
`,Summary=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  gap: ${({theme:theme})=>theme.space[3]};
`,Title=styled_components_browser_esm.zo.h1`
  font-size: ${({theme:theme})=>theme.fontSize["3xl"]};
  font-weight: bolder;
`,Address=styled_components_browser_esm.zo.h2`
  font-size: ${({theme:theme})=>theme.fontSize.lg};
`,LocationPinIcon=(0,styled_components_browser_esm.zo)(index_esm.OxZ)`
  margin-right: ${({theme:theme})=>theme.space[1]};
  font-size: ${({theme:theme})=>theme.fontSize.sm};
`;var components_CafeSummary=CafeSummary;try{CafeSummary.displayName="CafeSummary",CafeSummary.__docgenInfo={description:"",displayName:"CafeSummary",props:{title:{defaultValue:null,description:"",name:"title",required:!0,type:{name:"string"}},address:{defaultValue:null,description:"",name:"address",required:!0,type:{name:"string"}},onClick:{defaultValue:null,description:"",name:"onClick",required:!0,type:{name:"MouseEventHandler<HTMLDivElement>"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/CafeSummary.tsx#CafeSummary"]={docgenInfo:CafeSummary.__docgenInfo,name:"CafeSummary",path:"src/components/CafeSummary.tsx#CafeSummary"})}catch(__react_docgen_typescript_loader_error){}var CafeSummary_stories={title:"CafeSummary",component:components_CafeSummary};const Default={args:{title:"성수동 카페",address:"서울 성동구 연무장3길 6"}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    title: '성수동 카페',\n    address: '서울 성동구 연무장3길 6'\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-CafeSummary-stories.9f326489.iframe.bundle.js.map