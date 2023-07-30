"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[371],{"./src/components/LikedCafeList.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>LikedCafeList_stories});var styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const LikedCafeList=({likedCafes})=>(0,jsx_runtime.jsxs)(Container,{children:[(0,jsx_runtime.jsx)(TitleContainer,{children:(0,jsx_runtime.jsx)(Title,{children:"좋아요한 카페 리스트"})}),(0,jsx_runtime.jsx)(ScrollContainer,{children:(0,jsx_runtime.jsx)(GridContainer,{children:likedCafes.map((cafe=>(0,jsx_runtime.jsx)(CafeImage,{src:cafe.imageUrl,alt:`Cafe ${cafe.cafeId}`},cafe.cafeId)))})})]});LikedCafeList.displayName="LikedCafeList";const components_LikedCafeList=LikedCafeList,Container=styled_components_browser_esm.zo.section`
  display: flex;
  flex-direction: column;
  height: 100%;
`,Title=styled_components_browser_esm.zo.h1`
  margin-bottom: ${({theme})=>theme.space[3]};
  color: ${({theme})=>theme.color.gray};
`,TitleContainer=styled_components_browser_esm.zo.article`
  display: flex;
  justify-content: center;
  margin-bottom: ${({theme})=>theme.space[3]};
  border-bottom: solid ${({theme})=>theme.color.line.secondary};
`,ScrollContainer=styled_components_browser_esm.zo.div`
  overflow-y: scroll;
  flex: 1;

  &::-webkit-scrollbar {
    width: 0; /* 스크롤 바 너비 설정 */
  }
`,GridContainer=styled_components_browser_esm.zo.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 한 줄에 3개의 열 */
  gap: ${({theme})=>theme.space[.5]};
  margin-bottom: ${({theme})=>theme.space[10]};
`,CafeImage=styled_components_browser_esm.zo.img`
  aspect-ratio: 1 / 1;
`;try{LikedCafeList.displayName="LikedCafeList",LikedCafeList.__docgenInfo={description:"",displayName:"LikedCafeList",props:{likedCafes:{defaultValue:null,description:"",name:"likedCafes",required:!0,type:{name:"LikedCafe[]"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/LikedCafeList.tsx#LikedCafeList"]={docgenInfo:LikedCafeList.__docgenInfo,name:"LikedCafeList",path:"src/components/LikedCafeList.tsx#LikedCafeList"})}catch(__react_docgen_typescript_loader_error){}const LikedCafeList_stories={title:"LikedCafeList",component:components_LikedCafeList},Default={args:{}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {}\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-LikedCafeList-stories.80ac7edb.iframe.bundle.js.map