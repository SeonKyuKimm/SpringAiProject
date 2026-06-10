window.addEventListener('DOMContentLoaded', () => {
	const imageUrl = document.getElementById('image-url')?.value;
	
	if(imageUrl) {
		displayImagePreview(imageUrl);
	}
});

const imageUpload = document.getElementById('image-upload');

if(imageUpload) {
	imageUpload.addEventListener('change' ,async(e) => {
		const file = e.target.files[0];
		
		if(!file) return;
		
		if(!file.type.startsWith('image/')){
			alert('이미지 파일만 업로드 가능합니다.');
			return;
		}
		
		const formData = new FormData();
		formData.append('file', file);
		
		fetch('/api/upload', {
			method : 'POST',
			body :formData 
		}).then((resp) => {
			
			if(!resp.ok) {
				alert('이미지 업로드에 실패하였습니다');
				throw new Error();
			}
			
			return resp.json();

		}).then((data) => {
			document.getElementById('image-url').value = data.imageUrl;
			displayImagePreview(data.imageUrl);
		}).catch((e) => console.error(e))
	});
}

function displayImagePreview(imageUrl) {
	const prev = document.getElementById('image-preview');
	const prevImg = document.getElementById('preview-img');
	
	if(prev && prevImg && imageUrl) {
		prevImg.src = imageUrl;
		prev.style.display = 'block';
	}
}

const removeImageBtn = document.getElementById('remove-image-btn');
if(removeImageBtn) {
	removeImageBtn.addEventListener('click', () => {
		document.getElementById('image-url').value ='';
		document.getElementById('image-upload').value ='';
		document.getElementById('image-preview').style.display = 'none';
	});
}


const delBtn = document.getElementById("delete-btn");

if(delBtn) {
	delBtn.addEventListener('click', event => {
		let id = document.getElementById('article-id').value;
		
		fetch(`/api/articles/${id}`, { 
			method : 'DELETE'
		})
		.then( () => {
			alert('게시글이 삭제되었습니다');
			location.replace('/articles');
		});
	});
	
}

const modifyButton = document.getElementById('modify-btn');

if(modifyButton) {
	modifyButton.addEventListener('click', event => {
		let params = new URLSearchParams(location.search);
		let id = params.get('id');
		
		fetch(`/api/articles/${id}` , {
			method:"PUT",
			headers: {
				"Content-type" : "application/json" 
			},
			body: JSON.stringify({
				title : document.getElementById('title').value,
				content : document.getElementById('content').value
			})
		})
		.then( () => { alert("수정이 완료되었습니다.") });
		location.replace(`/articles/${id}`);
	});
}

const createBtn = document.getElementById('create-btn');

if(createBtn){
	createBtn.addEventListener('click', event => {
		fetch("/api/articles", {
				method : "POST",
				headers: {
					"content-type": "application/json",
				},
				body : JSON.stringify({
					title : document.getElementById("title").value,
					content : document.getElementById("content").value,
					imageUrl : document.getElementById("image-url").value
				}),
			}).then( () => {
				alert("등록되었습니다");
				location.replace("articles");
		})
	})
}


// AI 글작성 modal 창 Script
const aiAssistButton = document.getElementById('ai-assist-btn');

if(aiAssistButton){
	aiAssistButton.addEventListener('click', event => {
		$('#aiAssistModal').modal('show');
		document.getElementById('ai-suggestion').style.display='none';
		document.getElementById('ai-question').value ='';
	});
}

const getSuggestionBtn = document.getElementById('get-suggestion-btn');
if(getSuggestionBtn){
	getSuggestionBtn.addEventListener('click', event => {
		const title = document.getElementById('title').value;
		const content = document.getElementById('content').value;
		const question = document.getElementById('ai-question').value;
		
		if(!question.trim()){
			alert('고민 내용을 입력하세요');
			return;
		}
		
		document.getElementById('ai-loading').style.display='block';
		document.getElementById('ai-suggestion').style.display='none';
		
		const body = JSON.stringify({
			title : title,
			content: content,
			question : question
		});
		
		fetch('/api/ai-suggestions', {
			method : 'POST',
			headers: {
				'Content-type' : 'application/json',
			},
			body : body
		})
		.then(resp => {
			return resp.json();
		})
		.then(data => {
			document.getElementById('ai-loading').style.display ='none';
			const suggestionContent = document.getElementById('ai-suggestion-content');
			
			let html ='';
			if (data.suggestions && data.suggestions.length > 0) {
				html+= '<url class="list-group">';
				data.suggestions.forEach((suggestion, idx) => {
					html += `<li class="list-group-item suggestion-item"
						         style="cursor : pointer;" data-suggestion="${suggestion.replace(/"/g, '&quot;')}"
								 title="클릭하면 본문에 추가됩니다">
								 ${suggestion}
								 <small class ="text-muted float-right">클릭하여 추가</small>
							 </li>`;
				});
				html += '</ul>';
			}
			suggestionContent.innerHTML = html;
			document.getElementById('ai-suggestion').style.display='block';
		})
	});
}

// 제안 선택을 누르면 현재 내용 끝에 제안 추가
const suggestionContent = document.getElementById('ai-suggestion-content');

if(suggestionContent) {
	
	suggestionContent.addEventListener('click', function(e) {
		
		const suggestionItem = e.target.closest('.suggestion-item');
		
		if(suggestionItem) {
			const suggestion = suggestionItem.getAttribute('data-suggestion');
			const contentTextarea = document.getElementById('content');
			
			const currentContent = contentTextarea.value;
			const separator = currentContent && !currentContent.endsWith('\n') ? '\n\n' : '';
			
			contentTextarea.value = currentContent + separator + suggestion;
			
			$('#aiAssistModal').modal('hide');
			contentTextarea.focus();
		}
	});
}


