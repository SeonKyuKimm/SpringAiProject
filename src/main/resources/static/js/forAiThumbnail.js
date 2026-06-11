const aiThumbnailBtn = document.getElementById('ai-thumbnail-btn');

if(aiThumbnailBtn) {
	aiThumbnailBtn.addEventListener('click', async() => {
		
		const title =document.getElementById('title').value;
		const content = document.getElementById('content').value;
		
		if(!title.trim() && !content.trim()) {
			alert('제목 또는 내용을 먼저 입력해주세요');
			return;
		}
		
		// 로딩중을 표시하는 모달창
		const loadingDiv = document.getElementById('ai-thumbnail-loading');
		loadingDiv.style.display = 'block';
		aiThumbnailBtn.disabled = true;
		
		//ai 썸네일 호출
		fetch('/api/ai-thumbnails', {
			method : 'POST',
			body :JSON.stringify ({
				title : title,
				content : content
			}), 
			headers : {
				'Content-Type' : 'application/json',
			},
		}).then((resp) => {
			if(!resp.ok) {
				alert('썸네일 생성에 실패하였습니다.');
				throw new Error();
			}
			return resp.json();
		}).then((data) => {
			// 이미지 url 을 입력란에 설정하고 미리보기에 표시
			document.getElementById('image-url').value = data.imageUrl;
			displayImagePreview(data.imageUrl);
		}).finally(() => {
			loadingDiv.style.display = 'none';
			aiThumbnailBtn.disabled = false;
		});
	});
}