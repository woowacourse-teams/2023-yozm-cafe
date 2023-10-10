"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[694],{"./src/components/LikeButton.stories.tsx":function(__unused_webpack_module,__webpack_exports__,__webpack_require__){__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:function(){return Default},__namedExportsOrder:function(){return __namedExportsOrder},default:function(){return LikeButton_stories}});var react=__webpack_require__("./node_modules/react/index.js"),index_esm=__webpack_require__("./node_modules/react-icons/pi/index.esm.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const LikeButton=props=>{const{likeCount:likeCount,active:active,onChange:onChange}=props,[announce,setAnnounce]=(0,react.useState)("");(0,react.useEffect)((()=>{setAnnounce(active?"좋아요 취소되었습니다.":"좋아요가 추가되었습니다."),setTimeout((()=>setAnnounce("")),1e3)}),[active]);return(0,jsx_runtime.jsxs)(Container,{"aria-label":"좋아요 버튼",tabIndex:0,role:"button",children:[(0,jsx_runtime.jsx)(HeartIcon,{$active:active,onClick:()=>{onChange?.()}}),(0,jsx_runtime.jsx)(LikeCount,{children:likeCount}),(0,jsx_runtime.jsx)(Announcement,{"aria-live":"assertive","aria-atomic":"true","aria-relevant":"text",children:`${likeCount} ${announce}`})]})};LikeButton.displayName="LikeButton";var components_LikeButton=LikeButton;const HeartIcon=(0,styled_components_browser_esm.zo)(index_esm.r6X)`
  cursor: pointer;
  font-size: ${({theme:theme})=>theme.fontSize["4xl"]};
  color: ${({theme:theme,$active:$active})=>$active?theme.color.primary:theme.color.white};
`,LikeCount=styled_components_browser_esm.zo.span`
  font-size: small;
  color: ${({theme:theme})=>theme.color.white};
`,Container=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  padding-top: ${({theme:theme})=>theme.space[3]};
`,Announcement=styled_components_browser_esm.zo.div`
  position: absolute;
  left: -9999px;
`;try{LikeButton.displayName="LikeButton",LikeButton.__docgenInfo={description:"",displayName:"LikeButton",props:{likeCount:{defaultValue:null,description:"",name:"likeCount",required:!0,type:{name:"number"}},active:{defaultValue:null,description:"",name:"active",required:!0,type:{name:"boolean"}},onChange:{defaultValue:null,description:"",name:"onChange",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/LikeButton.tsx#LikeButton"]={docgenInfo:LikeButton.__docgenInfo,name:"LikeButton",path:"src/components/LikeButton.tsx#LikeButton"})}catch(__react_docgen_typescript_loader_error){}var LikeButton_stories={title:"LikeButton",component:components_LikeButton};const Default={args:{likeCount:1}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {\n    likeCount: 1\n  }\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-LikeButton-stories.d4fd0ee3.iframe.bundle.js.map