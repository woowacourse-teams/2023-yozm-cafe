"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[694],{"./src/components/LikeButton.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>LikeButton_stories});var react=__webpack_require__("./node_modules/react/index.js"),index_esm=__webpack_require__("./node_modules/react-icons/pi/index.esm.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const LikeButton=({likeCount})=>{const[isActive,setIsActive]=(0,react.useState)(!1);return(0,jsx_runtime.jsxs)(Container,{children:[(0,jsx_runtime.jsx)(HeartIcon,{$isActive:isActive,onClick:async()=>{setIsActive(!isActive)}}),(0,jsx_runtime.jsx)(LikeCount,{children:likeCount})]})};LikeButton.displayName="LikeButton";const components_LikeButton=LikeButton,HeartIcon=(0,styled_components_browser_esm.zo)(index_esm.r6X)`
  cursor: pointer;
  font-size: ${({theme})=>theme.fontSize["5xl"]};
  color: ${({theme,$isActive})=>$isActive?theme.color.primary:theme.color.secondary};
`,LikeCount=styled_components_browser_esm.zo.span`
  font-size: small;
  color: ${({theme})=>theme.color.gray};
`,Container=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;try{LikeButton.displayName="LikeButton",LikeButton.__docgenInfo={description:"",displayName:"LikeButton",props:{likeCount:{defaultValue:null,description:"",name:"likeCount",required:!0,type:{name:"number"}},onChange:{defaultValue:null,description:"",name:"onChange",required:!0,type:{name:"(likeCount: number) => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/LikeButton.tsx#LikeButton"]={docgenInfo:LikeButton.__docgenInfo,name:"LikeButton",path:"src/components/LikeButton.tsx#LikeButton"})}catch(__react_docgen_typescript_loader_error){}const LikeButton_stories={title:"LikeButton",component:components_LikeButton},Default={args:{currentLikeCount:1}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    currentLikeCount: 1\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-LikeButton-stories.e13296b2.iframe.bundle.js.map