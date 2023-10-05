"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[68],{"./src/components/ProfileInfo.stories.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:function(){return Default},__namedExportsOrder:function(){return __namedExportsOrder},default:function(){return ProfileInfo_stories}});var styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const ProfileInfo=props=>{const{userImage:userImage,userName:userName}=props;return(0,jsx_runtime.jsxs)(Container,{children:[(0,jsx_runtime.jsx)(Image,{src:userImage,alt:"프로필 이미지"}),(0,jsx_runtime.jsx)(Name,{children:userName})]})};ProfileInfo.displayName="ProfileInfo";var components_ProfileInfo=ProfileInfo;const Container=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`,Image=styled_components_browser_esm.zo.img`
  width: 120px;
  height: 120px;
  border-radius: 50%;
`,Name=styled_components_browser_esm.zo.span`
  margin-top: ${({theme:theme})=>theme.space[2.5]};
  font-size: ${({theme:theme})=>theme.fontSize.lg};
  font-weight: 600;
  color: ${({theme:theme})=>theme.color.text.primary};
`;try{ProfileInfo.displayName="ProfileInfo",ProfileInfo.__docgenInfo={description:"",displayName:"ProfileInfo",props:{userImage:{defaultValue:null,description:"",name:"userImage",required:!0,type:{name:"string"}},userName:{defaultValue:null,description:"",name:"userName",required:!0,type:{name:"string"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/ProfileInfo.tsx#ProfileInfo"]={docgenInfo:ProfileInfo.__docgenInfo,name:"ProfileInfo",path:"src/components/ProfileInfo.tsx#ProfileInfo"})}catch(__react_docgen_typescript_loader_error){}var ProfileInfo_stories={title:"ProfileInfo",component:components_ProfileInfo};const Default={args:{userImage:"/images/profile-example.png",userName:"김고니"}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    userImage: '/images/profile-example.png',\n    userName: '김고니'\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-ProfileInfo-stories.d85a7697.iframe.bundle.js.map