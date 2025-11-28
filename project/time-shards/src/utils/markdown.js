import MarkdownIt from 'markdown-it';
import hljs from 'highlight.js';
import 'highlight.js/styles/atom-one-dark.css'; // Choose a style

const md = new MarkdownIt({
    html: true,
    linkify: true,
    typographer: true,
    highlight: function (str, lang) {
        if (lang && hljs.getLanguage(lang)) {
            try {
                return '<pre class="hljs"><code>' +
                    hljs.highlight(str, { language: lang, ignoreIllegals: true }).value +
                    '</code></pre>';
            } catch (__) { }
        }

        return '<pre class="hljs"><code>' + md.utils.escapeHtml(str) + '</code></pre>';
    }
});

export const renderMarkdown = (content) => {
    return md.render(content || '');
};
