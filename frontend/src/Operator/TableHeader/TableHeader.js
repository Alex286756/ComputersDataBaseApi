import "./TableHeader.css";

export function TableHeader(props) {
    const title = props;
    return (
        <div className="table_header_panel">
            <div className="Caption">
                {title.title}
            </div>
        </div>
    )
}